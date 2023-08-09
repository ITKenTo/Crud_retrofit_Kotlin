package com.example.retrofit.Adapter

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.Interface.ApiService
import com.example.retrofit.Interface.ItemClick
import com.example.retrofit.Model.LoverModel
import com.example.retrofit.R
import com.example.retrofit.RetrofitHelper
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoverAdapter(private val list: List<LoverModel>, private val context: Context, val onclick:ItemClick):RecyclerView.Adapter<LoverAdapter.ViewHolder>() {
    private var progressDialog: ProgressDialog?=null
    private lateinit var apiService:ApiService
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img:ImageView= itemView.findViewById(R.id.img_view)
        val names:TextView= itemView.findViewById(R.id.name)
        val tuoi:TextView= itemView.findViewById(R.id.tuoi)
        val phongcach:TextView= itemView.findViewById(R.id.phongcach)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view= LayoutInflater.from(context).inflate(R.layout.item_lover, parent,false)
     return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        apiService=RetrofitHelper.getInstance().create(ApiService::class.java)
        holder.apply {
           Glide.with(context).load(list[position].image).into(img)
           names.setText(list[position].name)
            tuoi.setText(list[position].yearold.toString())
            phongcach.setText(list[position].type)
        }

        holder.itemView.setOnClickListener {
           // onclick.Onclik(position)
            update(position)
        }

    }

    private fun update (position: Int) {

            showLoading("đang tải")
            val  body = JsonObject().apply {
                addProperty("name", "Linh")
                addProperty("yearold", 20)
                addProperty("image", "https://taimienphi.vn/tmp/cf/aut/anh-gai-xinh-1.jpg")
                addProperty("type", "Cute")
            }
        CoroutineScope(Dispatchers.Main).launch {
            var result = apiService.updateLover(list[position].id.toString(), body)
            if (result.isSuccessful) {
                Log.e("DELETE", "${result.body()}",)
                notifyItemChanged(position)
            }
        }
            progressDialog?.dismiss()

    }
    private fun showLoading(msg:String){
        progressDialog= ProgressDialog.show(context,null,msg,true)
    }

}