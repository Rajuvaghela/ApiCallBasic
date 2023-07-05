package com.example.apicallbasic


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apicallbasic.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var moviesAdapter : MoviesRecyclerAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setUpViewModel()

        setUpRecyclerView()

        mainViewModel.responseContainer.observe(this, Observer {
            if (it != null){

                moviesAdapter.result = it.data

            }else{
                Toast.makeText(this, "There is some error!", Toast.LENGTH_SHORT).show()
            }
        })

        mainViewModel.isShowProgress.observe(this, Observer {
            if (it){
                activityMainBinding.mainProgressBar.visibility = View.VISIBLE

            }
            else{
                activityMainBinding.mainProgressBar.visibility = View.GONE

            }
        })


        mainViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        mainViewModel.getMoviesFromAPI()


    }

    private fun setUpRecyclerView() = activityMainBinding.movieRecyclerView.apply {
        moviesAdapter = MoviesRecyclerAdapter()
        adapter = moviesAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun setUpViewModel(){
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

}