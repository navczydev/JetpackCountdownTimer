/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

/**
 * @author Nav Singh
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        Log.d("", "MyApp: SURFACE")
        var counterValue by remember {
            mutableStateOf(0L)
        }
        var timerValue by remember {
            mutableStateOf(0L)
        }

        var timeFinished by remember {
            mutableStateOf(false)
        }

        val color: Color by animateColorAsState(
            when (timeFinished) {
                false -> listOf(
                    Color.Gray,
                    Color.Cyan,
                    Color.Red,
                    Color.DarkGray,
                    Color.Magenta,
                ).random()
                else -> Color.Green
            }
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = stringResource(R.string.app_title),
                style = typography.h4
            )
            Text(
                text = when (timeFinished) {
                    false -> "Timer Tick: $counterValue"
                    else -> "Challenge2 Done"
                },
                style = TextStyle(
                    fontSize = 32.sp,
                    color = color,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize()
            )

            Text(
                text = stringResource(R.string.timer_value_label),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Blue, fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(
                    onClick = {
                        Log.d(
                            "Start",
                            "MyApp: Start Timer"
                        )
                        counterValue = 0
                        timerValue = 10_000
                        timer(
                            timerValue,
                            {
                                counterValue = it
                            }
                        ) {
                            timeFinished = it
                        }
                    },
                    modifier = Modifier
                        .border(2.dp, Color.Blue, CircleShape)
                        .padding(4.dp)
                ) {
                    Text(text = "10")
                }

                Spacer(Modifier.size(4.dp))

                TextButton(
                    onClick = {
                        Log.d("Cancel", "MyApp: Cancel Timer")
                        timerValue = 30_000
                        counterValue = 0
                        timer(
                            timerValue,
                            {
                                counterValue = it
                            }
                        ) {
                            timeFinished = it
                        }
                    },
                    modifier = Modifier
                        .border(2.dp, Color.Red, CircleShape)
                        .padding(4.dp)
                ) {
                    Text(text = "30")
                }
                Spacer(Modifier.size(4.dp))

                TextButton(
                    onClick = {
                        Log.d("Cancel", "MyApp: Cancel Timer")
                        timerValue = 50_000
                        counterValue = 0
                        timer(
                            timerValue,
                            {
                                counterValue = it
                            }
                        ) {
                            timeFinished = it
                        }
                    },
                    modifier = Modifier
                        .border(2.dp, Color.Cyan, CircleShape)
                        .padding(4.dp)
                ) {
                    Text(text = "50")
                }
                Spacer(Modifier.size(4.dp))

                TextButton(
                    onClick = {
                        Log.d("Cancel", "MyApp: Cancel Timer")
                        timerValue = 100_000
                        counterValue = 0
                        timer(
                            timerValue,
                            {
                                counterValue = it
                            }
                        ) {
                            timeFinished = it
                        }
                    },
                    modifier = Modifier
                        .border(2.dp, Color.Gray, CircleShape)
                        .padding(4.dp)
                ) {
                    Text(text = "100")
                }
            }
        }
    }
}

fun timer(
    timerValue: Long,
    updaterCounterLambda: (tickValue: Long) -> Unit = {},
    timerFinished: (finished: Boolean) -> Unit
) {
    with(TimerManager) {
        when (timer) {
            null -> {
                Log.d("", "timer: First Time")
                timer = object : CountDownTimer(timerValue, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        Log.d("", "onTick: Tick")
                        updaterCounterLambda(millisUntilFinished / 1000)
                    }

                    override fun onFinish() {
                        Log.d("CounterTimer", "onFinish: I_AM_DONE")
                        timerFinished(true)
                    }
                }.start()
            }
            else -> {
                cancelTimer()
                Log.d("", "timer: ResetTimer")
                resetTimer(timerValue, updaterCounterLambda, timerFinished)
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

object TimerManager {
    var timer: CountDownTimer? = null
    fun resetTimer(
        timerValue: Long,
        updaterCounterLambda: (tickValue: Long) -> Unit = {},
        timerFinished: (finished: Boolean) -> Unit
    ) {
        timer = null
        timerFinished(false)
        timer = object : CountDownTimer(timerValue, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("", "onTick: Tick")
                updaterCounterLambda(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                Log.d("CounterTimer", "onFinish: I_AM_DONE")
                timerFinished(true)
            }
        }.start()
    }

    fun cancelTimer() {
        timer?.cancel()
    }
}
