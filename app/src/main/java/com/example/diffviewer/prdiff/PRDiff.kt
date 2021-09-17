package com.example.diffviewer.prdiff

class PRDiffLines {

    var lines : MutableList<String> = mutableListOf<String>()    // diff individual lines
}

// Should probably parse into a nice Array of objects for display
/*
class PRDiff {

    var file_a: String = ""             // file a
    var file_b: String = ""             // file 6

    var file_minus: String = ""         // diff file with additions
    var file_plus: String = ""          // diff file with subtractions

    var file_deleted: Boolean = false   // File was deleted

    var file_renamed: Boolean = false   // File was renamed only
    var rename_to: String = ""
    var rename_from: String = ""

    var at_at_line: String = ""         // @@ -116,7 +116,7 @@ for viewing
    var subtract_line: Int = 0
    var subtract_column: Int = 0
    var addition_line: Int = 0
    var addition_column: Int = 0

    var lines_changed : Array<String?> = emptyArray()    // lines changed

}
*/