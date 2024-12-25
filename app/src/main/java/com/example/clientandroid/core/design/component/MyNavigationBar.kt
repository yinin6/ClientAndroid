package com.example.clientandroid.core.design.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientandroid.feature.main.navigation.TopLevelDestination

/**
 * 底部导航栏
 */
@Composable
fun MyNavigationBar(
    destination: List<TopLevelDestination>,
    onNavigateToDestination:(Int) ->Unit,
    currentDestination: String?,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .navigationBarsPadding()
            .height(55.dp)
            .padding(bottom = 1.dp)


    ) {
        destination.forEachIndexed({ index, item ->
            val selected = currentDestination == item.route
            val color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }



            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onNavigateToDestination(index)
                    }
                    .padding(6.dp)
            ){
                Image(
                    painter = painterResource(id =
                    if (selected) {
                        item.selectedIcon
                    }else{
                        item.unselectedIcon
                    }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = item.titleTextId),
                    style = MaterialTheme.typography.labelSmall,
                    color = color
                )

            }
        })

    }

}



@Preview(showBackground = true)
@Composable
fun MyNavigationBarPreview(): Unit {
    MyNavigationBar(
        destination = TopLevelDestination.entries,
        currentDestination = TopLevelDestination.DISCOVERY.route,
        onNavigateToDestination = {}
    )
}