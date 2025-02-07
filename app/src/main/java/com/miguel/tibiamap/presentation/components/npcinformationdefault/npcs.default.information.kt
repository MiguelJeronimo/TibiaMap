package com.miguel.tibiamap.presentation.components.npcinformationdefault

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miguel.tibiamap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalNpcInformation(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,//Asigment to close the sheet when the user clicks outside the sheet
    sheetState: SheetState = rememberModalBottomSheetState(),
    showBottomSheet: MutableState<Boolean> = remember { mutableStateOf(false) },
    scope: CoroutineScope = rememberCoroutineScope(),
){
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Rashid",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineSmall
                )
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                        .clickable {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet.value = false
                                }
                            }
                        }
                )
            }
            LazyColumn {
                item {
                    GeneralInformationNPC(
                        modifier = Modifier.padding(10.dp).fillMaxWidth()
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(10.dp).align(Alignment.CenterHorizontally)
                    )
                }
                items(20){
                    ItemsListNpcInformation(
                        modifier = Modifier.padding(5.dp).fillMaxWidth().clickable{}
                    )
                }
            }
        }
    }
}

@Composable
fun GeneralInformationNPC(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Images NPCs",
            modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Notes You need to win his trust before selling any items to him. He travels to a different city everyday. This NPC changes his location everyday. On Mondays you can find him in Svargrond, in Dankwart's tavern, south of the temple. On Tuesdays you can find him in Liberty Bay, in Lyonel's tavern, west of the depot. On Wednesdays you can find him in Port Hope, in Clyde's tavern, west of the depot. On Thursdays you can find him in Ankrahmun, in Arito's tavern, above the post office. On Fridays you can find him in Darashia, in Miraia's tavern, south of the guildhalls. On Saturdays you can find him in Edron, in Mirabell's tavern, above the depot. On Sundays you can find him in Carlin depot, one floor above. To be able to trade with him, you must complete The Traveling Trader Quest.",
            modifier = Modifier.padding(bottom = 10.dp).align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ){
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "General Information",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Magician Quarter",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Gender: Magician Quarter",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Race: Human",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Job: Merchant",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Version: 8.10 December 11, 2007 Christmas Update 2007",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}


@Composable
fun ItemsListNpcInformation(
    modifier: Modifier = Modifier
){
    OutlinedCard(
        modifier = modifier
    ) {
      Row(
          modifier = modifier
      ){
          Image(
              painter = painterResource(id = R.drawable.ic_launcher_background),
              contentDescription = "Images NPCs",
              modifier = Modifier.weight(0.5f).padding(5.dp).size(50.dp)
          )
          Column (
              modifier = Modifier.weight(2f).padding(5.dp)
          ){
              Text(
                  text = "Wand of DragonBreath",
                  modifier = Modifier.padding(bottom = 5.dp).fillMaxWidth(),
                  style = MaterialTheme.typography.titleMedium
              )
              Text(
                  text = "2,500 golds coins",
                  modifier = Modifier.fillMaxWidth()
              )
          }
      }
    }
}

//previewss
@Preview(showBackground = true)
@Composable
fun previewList(){
    Column {
        GeneralInformationNPC(
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        )
        ItemsListNpcInformation(
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        )
    }
}