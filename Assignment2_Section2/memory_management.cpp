#include <iostream>

void printArray(int* arr, int size) {
    for (int i = 0; i < size; ++i)
        std::cout << arr[i] << " ";
    std::cout << std::endl;
}

int main() {
    int* data = new int[5]; 
    for (int i = 0; i < 5; ++i)
        data[i] = i + 1;

    printArray(data, 5);

    delete[] data; 

    return 0;
}
