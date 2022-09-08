#include <iostream>
#include <fstream>
#include "LinkedList.h"
#include "Fuel.h"
#include "Knapsack.h"

using namespace std;


int main() {
    const int CAPACITY = 100;
    fstream fs;
    fs.open("in.txt", ios::in);
    LinkedList<Fuel> *list = new LinkedList<Fuel>;
    while(!fs.eof()){
        int ind, m, c;
        fs >> ind >> m;
        list->add(Fuel(ind, m, m));
    }
    fs.close();
    fs.open("out.txt", ios::out);
    int truck = 1;
    while (list->getSize() > 0) {
        Fuel *fuel = list->toArray();
        fs << "на лесопилках лежат бревна: ";
        for (int i = 0; i < list->getSize(); i++){
            fs << "на " << fuel[i].n << " бревно с весом " << fuel[i].mass << ", ";
        }
        fs << '\n';
        fs << "грузовик №" << truck++ << " увез бревна: ";
        calc(fuel, list->getSize(), CAPACITY, list, fs);
        delete[] fuel;
        fs << '\n';
    }
    fs.close();
    delete list;
    return 0;
}
