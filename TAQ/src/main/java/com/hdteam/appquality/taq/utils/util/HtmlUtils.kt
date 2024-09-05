package com.hdteam.appquality.taq.utils.util

/***
Created by HungVV
Created at 16:55/05-09-2024
 ***/


internal class HtmlUtils {

    val table = StringBuilder()

    init {
        table.append("<table border=\"1\">")
    }

    fun returnTableHtml(): StringBuilder {
        table.append("</table>")
        return table
    }

    fun addRowTitle(listValue: List<String>) {
        table.append("<tr>")
        listValue.forEach {
            table.append("<th>${it}</th>")
        }
        table.append("</tr>")
    }


    fun addTitleColSpan(value: String, span: Int) {
        table.append("<tr>")
        table.append("<td align='center' colspan=\"${span}\">${value}</td>")
        table.append("</tr>")
    }

    fun addRow(listValue: List<String>) {
        table.append("<tr>")
        listValue.forEach {
            table.append("<td>${it}</td>")
        }
        table.append("</tr>")
    }

}