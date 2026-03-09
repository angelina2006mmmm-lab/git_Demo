public class Variables {

    public static void main(String[] args) {
        int[] names = new int[100];
        for (int i = 0; i <= names.length; i++){
            names[i] = i + 100;
            for (int s : names) {
                System.out.println(s);}
        }
    }
}



//    for (int i = 0; i < num.length; i++) {
//        System.out.print(num[i]);
//        if (i < num.length - 1){
//            System.out.print(", ");
//        } else {System.out.print(".");}
//    }
//    }
//    }
//        for (int i = 1000; i >= 0; i--){
//            if (i%3==0){
//            System.out.println(i);
//            }
//        }
//    }
//}


//        do {
//            System.out.println(i);
//            i++;
//        } while (i <= 1000);
//    }
//}

//        int weather = 10;
//        boolean sun = weather >= 15;
//        int time = 15;
//        boolean night = 23 >= time && time >= 5;
//        if (sun && !night) {
//            System.out.println("гулять");
//        }
//        System.out.println("читать книгу");



//        int year = 365;
//        int days = 10_000;
//        int x = days/year;
//        int y = days % year;
//        int z = y / 30;
//        int k = y - z * 30;
//        System.out.println(x);
//        System.out.println(z);
//        System.out.println(k);
//    }
