package ch.epfl.people.parser

import ch.epfl.people.network.people.model.People
import ch.epfl.people.utils.functional.StringUtils
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


object PeopleDataParser {

    fun parseJsoupDocumentToPeoplesList(document: Document?): List<People>? {
        if (document == null) return null
        val peoples = arrayListOf<People>()
        val hrefElements: Elements =
            document.select("div.entry-content").select("div.container").select("div.card-deck")

        for (i in 0 until hrefElements.size) {
            val user = People().apply {
                image =
                    hrefElements[i].select("div.card").select("div.card-body").select("div.my-3")
                        .select("img").first().absUrl("src")
                name =
                    hrefElements[i].select("div.card").select("div.card-body").select("h3").first()
                        .text()
            }

            val contact =
                hrefElements[i].select("div.card").select("div.card-body").select("div.w-100")
                    .select("a")
            val position = hrefElements[i].select("div.card").select("div.card-body")
                .select("dl.definition-list").select("dd")

            for (j in 0 until position.size) {
                if (j == 0)
                    user.position = position[j].text()
                else user.id = position[j].text()
            }

            for (j in 0 until contact.size) {
                if (j == 0)
                    user.email = StringUtils.convertToEmail(contact[j].attr("href")) ?: ""
                else user.contact = contact[j].attr("href").removePrefix("tel:")
            }

            peoples.add(user)
        }

        return peoples
    }
}