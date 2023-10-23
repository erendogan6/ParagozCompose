package com.ErenDogan.paragozcompose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.ErenDogan.paragozcompose.model.DovizModel
import com.ErenDogan.paragozcompose.model.ListeModel
import com.ErenDogan.paragozcompose.servis.DovizAPI
import com.ErenDogan.paragozcompose.ui.theme.ParagozComposeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = Color(0xFF393E46).toArgb()
        setContent {
            ParagozComposeTheme {
                VeriCek()
            }
        }
    }
}

@Composable
fun VeriCek(){
    val dovizListe = remember { mutableStateOf<List<DovizModel>>(listOf()) }

    val url = "https://api.collectapi.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DovizAPI::class.java)

    val call = retrofit.data()

    LaunchedEffect(Unit) {
        call.enqueue(object : Callback<ListeModel> {
            override fun onResponse(call: Call<ListeModel>, response: Response<ListeModel>) {
                println(response.message())
                println(response.code())
                if (response.isSuccessful){
                    response.body()?.let {
                        dovizListe.value = it.getDataList()!!.toMutableList()
                    }
                }
            }
            override fun onFailure(call: Call<ListeModel>, t: Throwable) {
                println(t.printStackTrace())
            }
        })
    }
    DovizListele(liste = dovizListe.value)
}

@Composable
fun DovizListele(liste: List<DovizModel>){
    LazyColumn(modifier=Modifier.fillMaxSize().systemBarsPadding()) {
        itemsIndexed(liste){index,item->
            val backgroundColor = if (index % 2 == 0) Color(0xFF393E46) else Color(0xFF222831)
            DovizRow(dovizModel = item, bgColor = backgroundColor)
        }
    }
}

@Composable
fun DovizRow(dovizModel: DovizModel,bgColor: Color){
    Column(modifier= Modifier
        .wrapContentHeight()
        .background(bgColor)
        .fillMaxWidth(),
    ) {
        dovizModel.name?.let {
            Text(text = it + " (${dovizModel.code})",
                modifier=Modifier.padding(10.dp,10.dp,10.dp,0.dp),
                color = Color(0xFF00ADB5),
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 26.sp,
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            dovizModel.buyingstr?.let { FiyatRow(price = it, type = 0)}
            dovizModel.sellingstr?.let { FiyatRow(price = it, type = 1)}
        }
    }
}

@Composable
fun FiyatRow(price:String,type: Int){
    val text = if (type==0){
        "Alış"
    }
    else{
        "Satış"
    }
    Text(text = "$text $price ₺",modifier=Modifier.padding(10.dp), fontFamily = FontFamily(Font(R.font.roboto)), fontSize = 20.sp, color = Color.White )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DovizRow(dovizModel =DovizModel("Amerikan Doları","27.0432","USD","27.0368"), bgColor = Color(0xFF393E46) )
}