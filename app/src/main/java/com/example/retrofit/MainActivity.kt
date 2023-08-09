package com.example.retrofit

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.Adapter.LoverAdapter
import com.example.retrofit.Interface.ApiService
import com.example.retrofit.Interface.ItemClick
import com.example.retrofit.Model.LoverModel
import com.example.retrofit.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var progressDialog: ProgressDialog?=null
    private lateinit var apiService: ApiService
    private lateinit var list: List<LoverModel>
    private lateinit var adapter: LoverAdapter
    private var position:Int?=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService= RetrofitHelper.getInstance().create(ApiService::class.java)
        binding.apply {
            button.setOnClickListener {
                getAll()
            }

            button2.setOnClickListener {
                AddLover()
            }

            button3.setOnClickListener {
                update()
            }

        }

            binding.recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)

    }
    private fun getAll() {
        lifecycleScope.launch {
             showLoading("đang tải")
            val result= apiService.getAll()
            if (result.isSuccessful) {
                list= result.body()!!
                Log.e("TAG", "getall ${result.body()!!.size}" )
                Log.e("TAG1", "getall ${list.toString()}" )
            }

            progressDialog?.dismiss()
            adapter= LoverAdapter(list,this@MainActivity,object :ItemClick{
                override fun Onclik(pos: Int) {
                    position=pos
                   Delete(pos)
                    Log.e("POS", "$pos", )
                }
            })
            binding.recyclerView.adapter=adapter
            adapter.notifyDataSetChanged()

        }
    }

    private fun AddLover(){
        lifecycleScope.launch {
            showLoading("đang tải")
            var body= JsonObject().apply {
                addProperty("name","Linh")
                addProperty("yearold",30)
                addProperty("image","https://taimienphi.vn/tmp/cf/aut/anh-gai-xinh-1.jpg")
                addProperty("type","Cute")
            }
            val result= apiService.creatLover(body)
            if (result.isSuccessful) {
                Log.e("POST", "${result.body()}", )
            }
            progressDialog?.dismiss()
        }
    }

    private fun update () {
        lifecycleScope.launch {
            showLoading("đang tải")
            val  body = JsonObject().apply {
                addProperty("name","Linh Hoa")
                addProperty("yearold",20)
                addProperty("image","https://taimienphi.vn/tmp/cf/aut/anh-gai-xinh-1.jpg")
                addProperty("type","Cute")
            }
            var result= apiService.updateLover("1", body)
            if (result.isSuccessful){
                Log.e("DELETE", "${result.body()}", )
            }
            progressDialog?.dismiss()
        }
    }
    private fun Delete(pos:Int){
        lifecycleScope.launch {
            showLoading("đang tải")

            var result= apiService.Delete(list[pos].id.toString())
            if (result.isSuccessful){
                Log.e("DELETE", "${result.body()}", )
            }
            progressDialog?.dismiss()
        }
    }

    private fun showLoading(msg:String){
        progressDialog= ProgressDialog.show(this,null,msg,true)
    }
}