package com.joaoxstone.jxscompose

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.JXSComposeTheme


const val titleTip =
    "O estado içado (State Hoisting) desta forma possui algumas propriedades importantes:"
val tipsList = listOf(
    "Fonte única de verdade: ao mover o estado em vez de duplicá-lo, garantimos que haja apenas uma fonte de verdade. Isso ajuda a evitar bugs.",
    "Compartilhável: o estado elevado pode ser compartilhado com vários elementos que podem ser compostos.",
    "Interceptável: os chamadores dos elementos que podem ser compostos sem estado podem decidir ignorar ou modificar eventos antes de alterar o estado.",
    "Desacoplado: o estado de uma função que pode ser composta sem estado pode ser armazenado em qualquer lugar. Por exemplo, em um ViewModel."
)


class StateCodelab : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JXSComposeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("State Codelab") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            )
                        )
                    }) {
                    Column(
                        verticalArrangement = Arrangement.Top, modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        WrongState()
                        Spacer(modifier = Modifier.padding(16.dp))
                        CorrectState()
                        TipCard(titleTip, tipsList)
                        TipCard(
                            "Ponto-chave:",
                            listOf("Uma prática recomendada para o design de Composables é passar a eles apenas os parâmetros necessários.")
                        )
                        Spacer(modifier = Modifier.padding(16.dp))
                        WellnessTasksList()
                    }
                }
            }
        }
    }
}


//É uma boa pratica receber o modifier como parametro da composable, isso aumenta o reuso, 
// e torna este parametro opcional
@Composable
fun WrongState(modifier: Modifier = Modifier) {
    var counter = 0

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Wrong state counter! Counter: $counter",
            modifier = modifier.padding(16.dp)
        )
        Spacer(modifier = modifier.padding(8.dp))
        JXSButton(onClick = { counter++ }, message = "Add One", Icons.Default.Add)
    }
}

@Composable
fun StatelessCounter(
    counter: Int,
    onClear: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Correct state counter! Counter: $counter",
            modifier = modifier.padding(16.dp)
        )
        Spacer(modifier = modifier.padding(8.dp))
        Row {
            JXSButton(onClick = { onIncrement() }, message = "Add One", Icons.Default.Add)
            Spacer(modifier = modifier.padding(8.dp))
            if (counter > 0) {
                JXSButton(onClick = { onClear() }, message = "Clear", Icons.Default.Delete)
            }
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }
    StatelessCounter(count, onIncrement = { count++ }, onClear = { count = 0 },
        modifier = modifier
    )
}

@Composable
fun CorrectState(modifier: Modifier = Modifier) {
    StatefulCounter()
}

@Composable
fun TipCard(title: String, content: List<String>) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic
            )
            if (isExpanded) {
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                Column {
                    content.forEach {
                        Text(text = it, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun WellnessTaskItem(taskName: String, modifier: Modifier = Modifier) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue },
        onClose = {},
        modifier = modifier,
    )
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

data class WellnessTask(val id: Int, val label: String)

fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(list) { task ->
            WellnessTaskItem(taskName = task.label)
        }
    }
}