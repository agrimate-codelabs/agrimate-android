package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.ResponseMessage
import com.codelabs.core.data.source.remote.response.news.NewsItemResponse
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor() {
    suspend fun getNews(): Response<List<NewsItemResponse>> {
        return coroutineScope {
            val data = listOf(
                NewsItemResponse(
                    url = "https://lestari.kompas.com/read/2024/02/10/110000486/jejak-karbon-urban-farming-6-kali-lipat-lebih-besar-dari-pertanian",
                    title = "Jejak Karbon \"Urban Farming\" 6 Kali Lipat Lebih Besar dari Pertanian Konvensional",
                    date = "10 Feb 2024",
                    source = "Kompas.com",
                    image = "https://asset.kompas.com/crops/OUkFxUE4t75wS94ReOUH0avPNK8=/0x0:799x533/750x500/data/photo/2022/03/01/621de02c72a00.jpg"
                ),
                NewsItemResponse(
                    url = "https://www.detik.com/edu/detikpedia/d-7169826/pakar-ipb-usung-teknologi-ini-buat-ekspor-global-sayur-buah-tanpa-residu-kimia",
                    title = "Pakar IPB Usung Teknologi Ini Buat Ekspor Global Sayur-Buah, Tanpa Residu Kimia ",
                    date = "01 Feb 2024",
                    source = "detikEdu",
                    image = "https://akcdn.detik.net.id/community/media/visual/2017/05/31/313dd530-85c0-49b4-ad18-52294da0806f_169.jpg?w=700&q=90"
                ),
                NewsItemResponse(
                    url = "https://www.detik.com/edu/detikpedia/d-7161534/pakar-unair-anak-muda-enggan-jadi-petani-sejak-1830-an-karena-model-pendidikan",
                    title = "Pakar Unair: Anak Muda Enggan Jadi Petani sejak 1830-an karena Model Pendidikan",
                    date = "26 Jan 2024",
                    source = "detikEdu",
                    image = "https://akcdn.detik.net.id/community/media/visual/2021/05/22/budi-daya-tanaman-mint.jpeg?w=700&q=90"
                ),
            )
            Response(
                code = 200,
                status = "success",
                message = ResponseMessage.StringMessage("Success Get Data"),
                data = data,
            )
        }
    }
}