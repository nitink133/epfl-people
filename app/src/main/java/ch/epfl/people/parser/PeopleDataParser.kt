package ch.epfl.people.parser

import ch.epfl.people.network.base.ApiConstants
import ch.epfl.people.network.people.model.People
import ch.epfl.people.utils.functional.LogUtils
import ch.epfl.people.utils.functional.StringUtils
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


object PeopleDataParser {

    fun parseJsoupDocumentToPeoplesList(document: Document?): List<People>? {
        if (document == null) return null
        val peoples = arrayListOf<People>()
        val hrefElements: Elements =
            document.select("div.entry-content").select("div.container").select("div.card-deck")

        for (rootLayerIndex in 0 until hrefElements.size) {
            val subCategory =  hrefElements[rootLayerIndex].select("div.card")
            for (subCategoryIndex in 0 until subCategory.size) {

                val user = People().apply {
                    imageUrl =
                        subCategory[subCategoryIndex].select("div.card-body")
                            .select("div.my-3")
                            .select("img").first().absUrl("src")
                    name =
                        subCategory[subCategoryIndex].select("div.card-body").select("h3")
                            .first()
                            .text()
                }
                val contact =
                    subCategory[subCategoryIndex].select("div.card-body").select("div.w-100")
                        .select("a")
                val position = subCategory[subCategoryIndex].select("div.card-body")
                    .select("dl.definition-list").select("dd")

                for (j in 0 until position.size) {
                    if (j == 0)
                        user.position = position[j].text()
                    else user.office = position[j].text()
                }

                for (j in 0 until contact.size) {
                    if (j == 0)
                        user.email = StringUtils.convertToEmail(contact[j].attr("href")) ?: ""
                    else user.contactNumber = contact[j].attr("href").removePrefix("tel:")
                }

                peoples.add(user)
            }
        }

        LogUtils.data("Nitin", peoples)
        return peoples
    }

    fun parseJsoupDocumentToPeoplesInfo(document: Document?): People? {
        if (document == null) return null
        return People().apply {
            name = document.select(ApiConstants.SelectorPath.NAME).text()
            biography = document.select(ApiConstants.SelectorPath.BIOGRAPHY).text().replace("Biography ","")
            citizenship = document.select(ApiConstants.SelectorPath.CITIZENSHIP).text().replace("Citizenship: ","")
            contactNumber = document.select(ApiConstants.SelectorPath.CONTACT_NUMBER).attr("href").replace("callto:","")
            email = document.select(ApiConstants.SelectorPath.EMAIL).attr("href").replace("mailto:","")
            fieldOfExpertise = document.select(ApiConstants.SelectorPath.FIELD_OF_EXPERTISE).text()
            imageUrl = document.select(ApiConstants.SelectorPath.IMAGE_URL).first().absUrl("src")
            linkedinProfile = document.select(ApiConstants.SelectorPath.LINKEDIN_URL).attr("href")
            mission = document.select(ApiConstants.SelectorPath.MISSION).text().replace("Mission ","")
            position = document.select(ApiConstants.SelectorPath.POSITION).text()
            twitterProfile = document.select(ApiConstants.SelectorPath.TWITTER_URL).attr("href")
            website = document.select(ApiConstants.SelectorPath.WEBSITE).text()
        }
    }
}