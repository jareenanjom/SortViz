package com.kapps.mergesort

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapps.mergesort.ui.theme.QuickSortTheme
import com.kapps.mergesort.ui.theme.gray
import com.kapps.mergesort.ui.theme.orange

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = orange.toArgb()
        window.navigationBarColor = orange.toArgb()

        setContent {
            QuickSortTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gray)
                        .padding(40.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.sortvizicon), // Replace with your logo resource
                        contentDescription = "App Logo",
                        modifier = Modifier.size(300.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "A Mobile Application for Visualizing Sorting Algorithms",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFAEC6CF),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            val intent = Intent(this@MainActivity, SortActivity::class.java)
                            startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = orange,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.defaultMinSize()
                    ) {
                        Text(
                            text = "START",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
