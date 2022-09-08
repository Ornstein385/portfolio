#pragma once
#include <iostream>
#include "LinkedList.h"
#include "Fuel.h"
#include <fstream>

void traceResult(int **dp, Fuel *f, int n, int s, LinkedList<Fuel> *list, fstream &fs);

void calc(Fuel *f, int n, int s, LinkedList<Fuel> *list, fstream &fs);
