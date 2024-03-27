package com.joaoxstone.jxscompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.JXSComposeTheme

class MainActivity : ComponentActivity() {

    val lista = listOf(
        CardData(
            "CodeLab Compose",
            "Conteúdo que estou aprendendo no codelab da google sobre Jetpack Compose",
            CodelabCompose::class.java
        ),
        CardData(
            "CodeLab State Compose",
            "Aprendendo a gerenciar o estado da aplicação com Jetpack Compose (ViewModel e State<>)",
            StateCodelab::class.java
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JXSComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {
                        items(lista) { item ->
                            JXSCard(
                                title = item.title,
                                description = item.description,
                                activity = item.activity
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun JXSCard(title: String, description: String, activity: Class<*>) {
        //rememberSaveable usado em mudanças de config: Tema, Orientação de tela...
        var isExpanded by rememberSaveable { mutableStateOf(false) }
        Card(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .clickable {
                        isExpanded = !isExpanded
                    }
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = title, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            modifier = Modifier.rotate(if (isExpanded) 180f else 0f)
                        )
                    }
                }
                if (isExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), horizontalAlignment = Alignment.End
                    ) {

                        Text(text = description, modifier = Modifier.padding(8.dp))
                        JXSButton(onClick = {
                            val intent = Intent(applicationContext, activity)
                            startActivity(intent)
                            //finish()
                        }, icon = Icons.AutoMirrored.Filled.KeyboardArrowRight)

                    }
                }
            }
        }

    }
}


data class CardData(val title: String, val description: String, val activity: Class<*>)

