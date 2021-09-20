package com.example.diffviewer.prdiff

class ChildModel(diif_line_1: String, diif_line_2: String) {
    private var diif_line_1: String? = diif_line_2
    private var diif_line_2: String? = diif_line_2

    fun getDiffLine1(): String? {
        return diif_line_1
    }

    fun getDiffLine2(): String? {
        return diif_line_2
    }
}