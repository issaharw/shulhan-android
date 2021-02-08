function showSearch() {
    settingsDiv.hide()    
    menuDiv.hide()
    containerDiv.hide()
    searchDiv.show()
    var searchText = $("#searchInput").val().trim()
    if (searchText == "")
        updateTitle('חיפוש')
    else
        updateTitle('חיפוש', '"' + searchText + '"')
}

function search() {
    var searchText = $("#searchInput").val().trim()
    if (searchText == "")
        return
    
    updateTitle('חיפוש', '"' + searchText + '"')
    var listMjMn = findText(searchText)
    buildResults(listMjMn)
}

function findText(text) {
    var results = []
    for(var i = 0; i < shulhanSearch.search.length; i++) {
        var majorMinor = {
            "major": shulhanSearch.search[i].major,
            "minor": shulhanSearch.search[i].minor,
            "title": shulhanSearch.search[i].title,
            "dataPositions": []
        }
        for(var j = 0; j < shulhanSearch.search[i].texts.length; j++) {
            if (shulhanSearch.search[i].texts[j].includes(text)) {
                majorMinor.dataPositions.push(j)
            }
        }
        if (majorMinor.dataPositions.length > 0)
            results.push(majorMinor)
    }
    return results
}

function buildResults(results) {
    searchResultsDiv.empty()
    var list = $("<ul class='menuList'>")
    searchResultsDiv.append(list)
    for(var i = 0; i < results.length; i++) {
        var item = $("<li>")
        list.append(item)
        var resultElem = $("<span class='menuItem'>").text(results[i].title)
                                .attr("onclick", "showContent(" + results[i].major + ", " + results[i].minor + ", [" + results[i].dataPositions + "])")
        item.append(resultElem)
    }

}

function clearSearchInput() {
    var searchText = $("#searchInput").val("")
    updateTitle('חיפוש')
    searchResultsDiv.empty()
}