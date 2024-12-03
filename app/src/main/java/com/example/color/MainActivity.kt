package com.example.color

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.color.ui.theme.COLORTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HCTpalette()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HCTpalette() {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                Text("HCT Color Palette",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }
        },
        content = { paddingValues ->
            ColorControls(modifier = Modifier.padding(paddingValues))
        }
    )
}

@Composable
fun ColorControls(modifier: Modifier = Modifier) {
    var hue by remember { mutableStateOf(180f) }
    var chroma by remember { mutableStateOf(8f) }
    var tone by remember { mutableStateOf(50f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Hue 滑动条
        Text("Hue: %.1f".format(hue), style = TextStyle(fontWeight = FontWeight.Bold))
        Slider(
            value = hue,
            onValueChange = { hue = it },
            valueRange = 0f..360f,
            modifier = Modifier.fillMaxWidth()
        )

        // Chroma 滑动条
        Text("Chroma: %.1f".format(chroma), style = TextStyle(fontWeight = FontWeight.Bold))
        Slider(
            value = chroma,
            onValueChange = { chroma = it },
            valueRange = 0f..16f,
            modifier = Modifier.fillMaxWidth()
        )

        // Tone 滑动条
        Text("Tone: %.1f".format(tone), style = TextStyle(fontWeight = FontWeight.Bold))
        Slider(
            value = tone,
            onValueChange = { tone = it },
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth()
        )

        // 显示颜色预览
        ColorPreview(hue, chroma, tone)
    }
}

@Composable
fun ColorPreview(hue: Float, chroma: Float, tone: Float) {
    val hct = Hct.from(hue.toDouble(), chroma.toDouble(), tone.toDouble())
    // Hct 中通过 toInt() 方法将颜色转换为 argb
    val argb = hct.toInt()

    // argb 共有32位，其中前8位为 alpha 通道，后24位为 RGB 通道
    // Kotlin 中通过 shr (shift right) 操作符将 argb 右移 n 位，即 argb shr n
    val red = (argb shr 16) and 0xFF
    val green = (argb shr 8) and 0xFF
    val blue = argb and 0xFF

    // 将 RGB 转换为 Compose 的 Color 对象
    // Compose 的 Color 类需要的是一个 Float 类型的值，范围从 0.0f 到 1.0f
    // 因此需要将 RGB 的值除以 255
    val color = Color(
        red = red / 255f,
        green = green / 255f,
        blue = blue / 255f,
        alpha = 1f // 设置 alpha 为不透明
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Preview Color",
            color = Color.White,
            style =  TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHCTColorPaletteApp() {
    HCTpalette()
}

//@Composable
//fun ExtendedExample(onclick: () -> Unit) {
//    ExtendedFloatingActionButton(
//        onClick = { onclick() },
//        icon = { Icon(Icons.Filled.Edit,"Open image") },
//        text = { Text("Open image") },
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    ExtendedExample( onclick = {  } )
//}
