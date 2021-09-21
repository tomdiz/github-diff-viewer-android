package com.example.diffviewer.prdiff

class PRDiff() {

    var lines : ArrayList<String> = arrayListOf<String>()    // diff individual lines

    var diffFileName: String = ""
    var indexString: String = ""

    var changedfile: Int = 0

    var file_deleted: Boolean = false   // File was deleted

    var file_minus: String = ""         // diff file with additions
    var file_plus: String = ""          // diff file with subtractions

    var rename_to: String = ""          // It set file has just been renamed
    var rename_from: String = ""
}
