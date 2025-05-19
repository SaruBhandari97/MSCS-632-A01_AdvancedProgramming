#include <iostream>
#include <vector>

void modifyVector(std::vector<std::string>& vec) { 
    vec.push_back("Saru");
}

int main() {
    std::vector<std::string> data = {"Hello"};
    modifyVector(data);
    for (const auto& str : data) {
        std::cout << str << " ";
    }
    return 0; 
}
