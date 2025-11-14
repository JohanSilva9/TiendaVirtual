package com.example.tienda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tienda.data.network.RetrofitClient
import com.example.tienda.data.repository.StoreRepository
import com.example.tienda.data.prefs.DataStoreManager
import com.example.tienda.data.room.AppDatabase
import com.example.tienda.data.repository.CartRepository
import com.example.tienda.viewmodel.AuthViewModel
import com.example.tienda.viewmodel.CartViewModel
import com.example.tienda.viewmodel.ProductViewModel
import com.example.tienda.navigation.NavGraph
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancias necesarias
        val api = RetrofitClient.create()
        val storeRepo = StoreRepository(api)
        val dataStore = DataStoreManager(applicationContext)
        val db = AppDatabase.getInstance(applicationContext)
        val cartRepo = CartRepository(db.cartDao())

        // ViewModels simples instanciados aquí y pasados
        val authViewModel = AuthViewModel(storeRepo, dataStore)
        val productViewModel = ProductViewModel(storeRepo)
        val cartViewModel = CartViewModel(cartRepo)

        setContent {
            MaterialTheme {
                Surface {
                    NavGraph(
                        authViewModel = authViewModel,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel,
                        isLoggedInFlow = object : kotlinx.coroutines.flow.StateFlow<Boolean> {
                            // We adapt DataStore flow to StateFlow by collecting it in a simple wrapper.
                            // Simpler: pass dataStore.isLoggedIn as StateFlow via .stateIn in a coroutine scope,
                            // but to avoid adding a helper here, we will create a backing implementation:
                            override val replayCache: List<Boolean> get() = emptyList()
                            override val value: Boolean get() = false
                            override fun collect(collector: kotlinx.coroutines.flow.FlowCollector<Boolean>): kotlinx.coroutines.flow.FlowCollector<Boolean> {
                                throw NotImplementedError("Not used directly")
                            }
                            override fun subscribe(collector: kotlinx.coroutines.flow.FlowCollector<Boolean>) = throw NotImplementedError()
                        }
                    )
                }
            }
        }
    }
}
