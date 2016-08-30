PET(new PetS(id: 10, type: "dog", name: "Жучка", color: "Black", sex: "F", breed: "Rottweiler",
            bowl:[new BowlS(id:1, petId: 10, color: "Silver", material: "Steel", size: "L")]))

class PetS {
    def id
    def type
    def name
    def color
    def sex
    def breed
    BowlS[] bowl
}

class BowlS {
    def id
    def petId
    def color
    def material
    def size
}
