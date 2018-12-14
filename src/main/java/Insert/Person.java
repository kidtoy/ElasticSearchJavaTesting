package Insert;

//	Three steps when creating an object from a class
//Declaration - A variable declaration with a variable name with an object type.
//Instantiation - The 'new' keyword is used to create the object.
//Initialization The 'new' keyword is followed by a call to a constructor. This call initializes the new object.

//Class
//can be defined as a template that describes the behavior/state that the object of its type supports.
public class Person {
        String name;
        int age;
        String lastName;

        //			Constructors
//		construtores são os responsáveis por criar o objeto em memória, ou seja, instanciar a classe que foi definida.
        public Person() {
        }

        public Person(String name, int age, String lastName) {
            name = this.name;
            age = this.age;
            lastName = this.lastName;
        }

        //	Gets and sets
        public void setAge( int setAge ) {
            age = setAge;
        }

        public int getAge( ) {
            return age;
        }

        //			Methods
//		is basically a behavior. A class can contain many methods. It is in methods where the logics are written, data is manipulated and all the actions are executed.
        void presentation() {
            System.out.println("Oi eu sou o "+ this.name);
        }
        void walk(int distance) {
            System.out.println("Eu vou andar "+distance+" metros");
        }
}

class teste{
    public static void main(String[] args){
        Person person = new Person("zulu",22,"Italo");
        person.presentation();
    }
}

//
//public class Humano {
//
//    private String name;
//    private int age;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public Humano(String name, int age){
//        this.name = name;
//        this.age = age;
//
//    }
//
//    public void seApresentar(){
//        System.out.println(this.name + this.age);
//    }
//}
//
//class teste{
//     public static void main(String[] args){
//         Humano humano = new Humano("Zulu", 21);
//         humano.seApresentar();
//     }
//}
