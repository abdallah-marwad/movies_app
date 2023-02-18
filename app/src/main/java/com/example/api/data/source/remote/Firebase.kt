package com.example.api.data.source.remote

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.api.data.source.local.favorite.FavoriteModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class Firebase {
    lateinit var userId: String
    private val userCollectionRef = Firebase.firestore
    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + parentJob)
    var userData = MutableLiveData<List<FavoriteModel>>()
    var foundUser = MutableLiveData<Boolean>()


    fun saveMovie(model: FavoriteModel) = coroutineScope.launch {
        try {

            userCollectionRef.collection(userId).add(model).await() // make the coroutine wait
            withContext(Dispatchers.Main) {
                Log.d("err", "successfully save")

            }
        } catch (e: java.lang.Exception) {
            Log.d("err", "Error is : ${e.message}")

        }
    }

    fun subscribeToRealTimeUpdates() {
        userCollectionRef.collection(userId).addSnapshotListener { value, error ->
            error?.let { exception ->
                Log.d("test", "Error is : ${exception.message}")
                return@addSnapshotListener
            }
            value?.let {
                userData.postValue(it.toObjects(FavoriteModel::class.java))

            }
        }
    }

    fun deleteMovie(model: FavoriteModel) =
        coroutineScope.launch {
            val userQuery =
                userCollectionRef.collection(userId).whereEqualTo("movieId", model.movieId)
                    .get().await()

            if (userQuery.documents.isNotEmpty()) {
                try {
                   userCollectionRef.collection(userId).document(userQuery.documents[0].id).delete()
                } catch (e: java.lang.Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("test", "Error is : ${e.message} ")
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.d("test", "document is empty")
                }
            }
        }

    fun checkIfMovieIdInFirebase(model: FavoriteModel) {

        coroutineScope.launch {
            try {
                val userQuery =
                    userCollectionRef.collection(userId).whereEqualTo("movieId", model.movieId)
                        .whereEqualTo("movieName", model.movieName)
                        .get().await()
                Log.d("test", "number of queries: ${userQuery.documents.size}")

                if (userQuery.documents.isNotEmpty()) {
                    foundUser.postValue(true)
                }


            } catch (e: java.lang.Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("test", "Error is : ${e.message}")
                }
            }
        }


    }

}