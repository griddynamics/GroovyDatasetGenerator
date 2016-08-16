_cassandra {
    (2..9).each {
        PET(id: it, type: "cat", name: "Васька №${it}", color: "Gray", sex: "M", breed: "American Bobtail")
    }
}
