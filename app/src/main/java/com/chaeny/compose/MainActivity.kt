package com.chaeny.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chaeny.compose.ui.theme.ComposeTheme

//androidx.compose.*: 컴파일러 및 런타임 클래스의 경우
//androidx.compose.ui.*: UI 도구 키트 및 라이브러리의 경우

// Compose를 사용하면 Activity가 Android 앱의 진입점으로 유지됩
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme { //프로젝트이름 + Theme
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    // 구성변경 후에도 온보딩화면 표시되지 않음
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

// Myapp에서는 shouldShowOnboarding에 액세스할 수 없다
// OnboardingScreen에서 만든 상태를 MyApp 컴포저블과 공유해야 한다
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn (modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

// 각 Greeting은 서로 다른 UI 요소에 속하기 때문에 자체적으로 펼쳐진 상태 유지
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // 구성변경 후에도 expaned가 유지됨
    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding = if (expanded) 48.dp else 0.dp
    // 간단한 계산을 실행하므로 리컴포지션에 대비하여 이 값을 기억할 필요가 없음

    Surface(
        color = MaterialTheme.colorScheme.primary, // 배경 색상 설정
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    ComposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}
