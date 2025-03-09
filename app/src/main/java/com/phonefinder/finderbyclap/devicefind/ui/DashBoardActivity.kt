package com.phonefinder.finderbyclap.devicefind.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.phonefinder.finderbyclap.devicefind.R
import com.phonefinder.finderbyclap.devicefind.ui.theme.AppTheme

class DashBoardActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent{
			AppTheme {
				Surface(modifier = Modifier.fillMaxSize()) {
					DashBoard(modifier = Modifier.fillMaxSize().padding(8.dp))
				}
			}
		}
	}

	fun share() {
		val shareIntent = Intent(Intent.ACTION_SEND)
		shareIntent.setType("text/plain")
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
		var shareMessage = "\nLet me recommend you this application\n\n"
		shareMessage =
			shareMessage + "https://play.google.com/store/apps/details?id=" + packageName + "\n\n"
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
		startActivity(Intent.createChooser(shareIntent, "Choose one"))
	}

}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashBoard(modifier: Modifier = Modifier) {
	val activityContext = LocalContext.current as DashBoardActivity
	Surface(modifier = modifier) {
		Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

			Row(modifier = Modifier
				.fillMaxWidth()
				.height(32.dp),
				horizontalArrangement = Arrangement.Start)
			{
				IconButton(
					onClick = {
						TODO("Implement the menu button opening")
					},
					modifier = Modifier
						.fillMaxHeight()
						.aspectRatio(1f))
				{
					Icon(Icons.Sharp.Menu, null, modifier = Modifier.fillMaxSize())
				}
				Text(modifier = Modifier
					.fillMaxSize()
					.wrapContentHeight(Alignment.CenterVertically),
					text = LocalContext.current.getString(R.string.app_name),
					textAlign = TextAlign.Center)
			}
			Column(modifier = Modifier
				.fillMaxSize()
				.wrapContentSize(Alignment.Center)) {
				Image(painterResource(R.drawable.started), null, modifier = Modifier
					.fillMaxWidth()
					.height(150.dp).clickable {
						val intent = Intent(activityContext, MainActivity::class.java)
						startActivity(activityContext, intent, null)

					})
				Row(modifier = Modifier
					.fillMaxWidth()
					.height(100.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
					data class Item(@DrawableRes val imageRes: Int, val action: ()->Unit)
					val items = listOf<Item>(
						Item(imageRes = R.drawable.settings_dash, action = {
							val intent = Intent(activityContext, SettingActivity::class.java)
							startActivity(activityContext, intent, null)
						}),
						Item(imageRes = R.drawable.privacy_dash, action = {
							val intent =
								Intent(activityContext, NewPrivacyPolicyActivity::class.java)
							startActivity(activityContext, intent, null)
						}),
						Item(imageRes = R.drawable.share_dash, action = {
							activityContext.share()
						}),
						)
					for( item in items) {
						Image(
							painterResource(item.imageRes),
							null,
							modifier = Modifier
								.height(150.dp)
								.aspectRatio(1f)
								.clickable{ item.action() } )
					}

				}
			}
		}
	}
}

@Preview(showSystemUi = true)
@Composable
fun DashBoardPreview(modifier: Modifier = Modifier) {
	DashBoard(
		modifier
			.fillMaxSize()
			.padding(16.dp))
}