package me.tbsten.gachagachazamurai.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
    extraLarge = CircleShape,
)

@Preview(widthDp = 200)
@Composable
fun ShapePreview() {
    Column {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            shape = shapes.extraSmall,
        ) {
            Text("extraSmall")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            shape = shapes.small,
        ) {
            Text("small")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            shape = shapes.medium,
        ) {
            Text("medium")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            shape = shapes.large,
        ) {
            Text("large")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            shape = shapes.extraLarge,
        ) {
            Text("extraLarge")
        }
    }
}
