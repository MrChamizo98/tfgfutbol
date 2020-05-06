package com.example.tfgfutbol;

public class GlobalInfo {

    public static int acceso_laliga=0;
    public static int clasif_laliga=0;
    public static int acceso_premier=0;
    public static int clasif_premier=0;
    public static int acceso_seriea=0;
    public static int clasif_seriea=0;
    public static int acceso_bundesliga=0;
    public static int clasif_bundesliga=0;
    public static int part_laliga=0;
    public static int part_premier=0;
    public static int part_bundesliga=0;
    public static int part_seriea=0;
    public static int plant_laliga=0;
    public static int plant_premier=0;
    public static int plant_bundesliga=0;
    public static int plant_seriea=0;

    public static void set_laliga(){
        acceso_laliga=1;
    }

    public static void set_premier(){
        acceso_premier=1;
    }

    public static void set_seriea(){
        acceso_seriea=1;
    }

    public static void set_bundesliga(){
        acceso_bundesliga=1;
    }

    public static int get_laliga(){
        return acceso_laliga;
    }

    public static int get_premier(){
        return acceso_premier;
    }

    public static int get_seriea(){
        return acceso_seriea;
    }

    public static int get_bundesliga(){
        return acceso_bundesliga;
    }

    public static void set_clasiflaliga(){
        clasif_laliga=1;
    }

    public static void set_clasifpremier(){
        clasif_premier=1;
    }

    public static void set_clasifseriea(){
        clasif_seriea=1;
    }

    public static void set_clasifbundesliga(){
        clasif_bundesliga=1;
    }

    public static int get_clasiflaliga(){
        return clasif_laliga;
    }

    public static int get_clasifpremier(){
        return clasif_premier;
    }

    public static int get_clasifseriea(){
        return clasif_seriea;
    }

    public static int get_clasifbundesliga(){
        return clasif_bundesliga;
    }

    public static void set_partlaliga(){part_laliga=1;}

    public static void set_partpremier(){part_premier=1;}

    public static void set_partbundesliga(){part_bundesliga=1;}

    public static void set_partseriea(){part_seriea=1;}

    public static int get_partlaliga(){return part_laliga;}

    public static int get_partpremier(){return part_premier; }

    public static int get_partbundesliga(){return part_bundesliga;}

    public static int get_partseriea(){return part_seriea;}

    public static void set_plantlaliga(){plant_laliga=1;}

    public static void set_plantpremier(){plant_premier=1;}

    public static void set_plantbundesliga(){plant_bundesliga=1;}

    public static void set_plantseriea(){plant_seriea=1;}

    public static int get_plantlaliga(){return plant_laliga;}

    public static int get_plantpremier(){return plant_premier; }

    public static int get_plantbundesliga(){return plant_bundesliga;}

    public static int get_plantseriea(){return plant_seriea;}
}
