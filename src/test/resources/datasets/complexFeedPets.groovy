import com.griddynamics.model.*

// Structure: #petId:["#petType", "#petName", [#feed1, #feed2, ...]]
def FeedPets = [
        1:["rat", "Лариска", ["Семечки", "Морковь"]],
        2:["cat", "Барсик", ["Royal Canin INDOOR 27"]],
        3:["boa", "Каа", []],
]

FeedPets.each { pet ->
    def pets = new Pet().petId(pet.key).type(pet.value[0]).name(pet.value[1])
    def bowlId = pet.key*10
    pet.value[2].each { food ->
        bowlId ++
        pets + new Bowl(id:bowlId, feed:(new Feed(bowlId:bowlId, food:food)))
    }
    PET(pets)
}
