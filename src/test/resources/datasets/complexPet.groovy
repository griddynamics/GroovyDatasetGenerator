import groovy.transform.TupleConstructor

PET(new Pet(id: 10, type: "dog", name: "Жучка", color: "Black", sex: "F", breed: "Rottweiler",
            bowl:[new Bowl(id:1, petId: 10, color: "Silver", material: "Steel", size: "L")]))

@TupleConstructor
class Pet {
    def id
    def type
    def name
    def color
    def sex
    def breed
    Bowl[] bowl
}

@TupleConstructor
class Bowl {
    def id
    def petId
    def color
    def material
    def size
}
