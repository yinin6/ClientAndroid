package com.example.clientandroid.feature.guide.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.clientandroid.feature.note.NoteDetailRoute
import com.example.clientandroid.feature.note.NoteDetailScreen
import com.example.clientandroid.ui.navigation.myComposable

const val NOTE_DETAIL_ROUTE = "note_detail_route"
const val NOTE_ROUTE = "note_route"

fun NavController.navigateToNoteDetailScreen(noteId: String) {
    navigate("$NOTE_DETAIL_ROUTE/$noteId")
}


fun NavGraphBuilder.noteDetailScreen(): Unit {
    myComposable(
        "$NOTE_DETAIL_ROUTE/{noteId}",
        arguments = listOf(navArgument("noteId") { type = NavType.StringType })
    ) {
            backStackEntry ->
        val poetryId = backStackEntry.arguments?.getString("noteId")
        println(poetryId)
        NoteDetailRoute(poetryId)
    }
}