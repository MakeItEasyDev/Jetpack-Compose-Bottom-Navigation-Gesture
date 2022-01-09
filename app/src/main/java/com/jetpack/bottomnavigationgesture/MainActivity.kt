package com.jetpack.bottomnavigationgesture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.bottomnavigationgesture.ui.theme.BottomNavigationGestureTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavigationGestureTheme {
                Surface(color = MaterialTheme.colors.background) {
                    BottomNavigationGesture()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomNavigationGesture() {
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val (isGestureEnable, toggleGestureEnable) = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Bottom Navigation Drawer",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .toggleable(
                    value = isGestureEnable,
                    onValueChange = toggleGestureEnable
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Checkbox(
                checked = isGestureEnable,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primaryVariant,
                    uncheckedColor = Color.DarkGray,
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = "Enable swipe up/down gesture",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 16.sp
            )
        }
        BottomDrawer(
            drawerContent = {
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = "Close icon",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.End)
                        .rotate(45f)
                        .clickable {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                )

                LazyColumn {
                    items(20) {
                        ListItem(
                            text = {
                                Text(
                                    text = "Make it Easy ${it + 1}"
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Info icon"
                                )
                            }
                        )
                        Divider(modifier = Modifier
                            .height(0.8.dp)
                            .background(Color.LightGray.copy(alpha = 0.5f)))
                    }
                }
            },
            gesturesEnabled = isGestureEnable,
            drawerState = drawerState,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Text(
                            text = "Click to open Bottom Drawer",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        )
    }
}


























