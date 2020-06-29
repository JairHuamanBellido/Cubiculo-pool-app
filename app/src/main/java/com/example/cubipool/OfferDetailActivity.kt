package com.example.cubipool

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.offers.CreateJoinOffer
import com.example.cubipool.service.offers.OfferDetailJoin
import com.example.cubipool.service.offers.OfferService
import kotlinx.android.synthetic.main.activity_offer_detail.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferDetailActivity : AppCompatActivity() {
    var id:Int = -1;

    var offerService =  ApiGateway().api.create<OfferService>(OfferService::class.java);
    var createJoinOffer =  CreateJoinOffer("-",false,false,2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_detail)
        val context =  this;
        offerDetail_board_container.visibility= View.GONE
        offerDetail_apple_container.visibility = View.GONE

        this.id =  intent.getStringExtra("id").toInt()

        createJoinOffer.codigo = getSharedPreferences("db_local",0).getString("code",null);
        createJoinOffer.ofertaId = this.id

        this.getOfferDetail()
        this.initVariables()

        btn_joinOffer.setOnClickListener { this.joinOffer(context) }
    }

    private fun getOfferDetail(){
        offerService.findByIdOffer(this.id).enqueue(object:Callback<OfferDetailJoin>{
            override fun onFailure(call: Call<OfferDetailJoin>, t: Throwable) {
                Log.d("tag", "Error")
            }

            override fun onResponse(call: Call<OfferDetailJoin>, response: Response<OfferDetailJoin>) {
                Log.d("tag" , response.body()!!.appleTv.toString());

                if(response.body()!!.appleTv == true){
                    offerDetail_apple_container.visibility = View.VISIBLE
                }
                if(response.body()!!.pizarra == true){
                    offerDetail_board_container.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun initVariables(){


        cb_AppleTV_offerDetail.setOnClickListener(View.OnClickListener {
            createJoinOffer.apple =  cb_AppleTV_offerDetail.isChecked
        })

        cb_Board_offerDetail.setOnClickListener(View.OnClickListener {
            createJoinOffer.pizarra  = cb_Board_offerDetail.isChecked
        })
    }

    private fun joinOffer(context: Context){

        Log.d("codigo", createJoinOffer.codigo);
        Log.d("apple", createJoinOffer.apple.toString());
        Log.d("pizarra", createJoinOffer.pizarra.toString());
        Log.d("ofertaId", createJoinOffer.ofertaId.toString());

        offerService.joinOffer(createJoinOffer).enqueue(object:Callback<JSONObject>{
            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                Log.d("tag", "qweqwe")
            }

            override fun onResponse(
                call: Call<JSONObject>,
                response: Response<JSONObject>
            ) {

                val intent = Intent(context,HomeActivity::class.java);
                startActivity(intent)
            }
        })

    }
}


