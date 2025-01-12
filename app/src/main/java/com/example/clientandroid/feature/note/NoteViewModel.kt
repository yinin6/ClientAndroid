package com.example.clientandroid.feature.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientandroid.core.model.LoginResponse
import com.example.clientandroid.core.model.Note
import com.example.clientandroid.core.model.PoetryData
import com.example.clientandroid.core.model.respond.NetworkResponse
import com.example.clientandroid.core.network.datasource.LocalDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel : ViewModel() {

    private val _id = MutableStateFlow<Int>(0)
    val id: MutableStateFlow<Int> = _id


    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private val _imageBase64 = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageBase64

    private var _loginRespond = MutableStateFlow<NetworkResponse<Note>?>(null)
    var loginRespond : StateFlow<NetworkResponse<Note>?> = _loginRespond


    private var _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateContent(newContent: String) {
        _content.value = newContent
    }

    fun updateImageUrl(newImageUrl: String?) {
        _imageBase64.value = newImageUrl
    }

    private companion object {
        const val TAG = "NoteViewModel"
    }

    init {
        _loginRespond.value = NetworkResponse<Note>(status = 0)
    }


    fun replayNote(note : Note) {
        _loginRespond.value = NetworkResponse<Note>(status = 0)
        _id.value = note.id
        _title.value = note.title
        _content.value = note.content
        _imageBase64.value = note.imageBase64
    }

    fun newNote() {
        _loginRespond.value = NetworkResponse<Note>(status = 0)
        _id.value = 0
        _title.value = ""
        _content.value = ""
        _imageBase64.value = null
    }

    fun saveNote(userId : String) {
        viewModelScope.launch {
            val note = _imageBase64.value?.let {
                Note(
                    id = _id.value,
                    title = _title.value,
                    content = _content.value,
                    imageBase64 = it,
                    username = userId
                )
            }
            if (note != null) {
                _loginRespond.value = LocalDatasource.saveDiaryEntry(note)
                Log.d(TAG, "saveNote: ${_loginRespond.value!!.message}")
            }
        }
    }

    fun getUserNote(userId : String) {
        viewModelScope.launch {
            _notes.value = LocalDatasource.getUserNote(userId).data ?: emptyList()
            Log.d(TAG, "getUserNote: ${_loginRespond.value!!.message}")
        }
    }

    fun delNote(note: Note) {
        viewModelScope.launch {
            val result = LocalDatasource.delNote(note.id)
            Log.d(TAG, "delNote: ${result.message}")
//            getUserNote(note.username)
        }

    }
}