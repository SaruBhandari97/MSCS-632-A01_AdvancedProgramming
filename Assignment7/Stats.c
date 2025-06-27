#include <stdio.h>
#include <stdlib.h>

// Function to compare integers for qsort
int compare(const void *a, const void *b) {
    return (*(int*)a - *(int*)b);
}

double calculate_mean(int arr[], int n) {
    int sum = 0;
    for (int i = 0; i < n; i++)
        sum += arr[i];
    return (double)sum / n;
}

double calculate_median(int arr[], int n) {
    qsort(arr, n, sizeof(int), compare);
    if (n % 2 == 0)
        return (arr[n/2 - 1] + arr[n/2]) / 2.0;
    else
        return arr[n/2];
}

void calculate_mode(int arr[], int n) {
    int max_count = 0;
    int mode_value = arr[0];
    int count;

    for (int i = 0; i < n; ++i) {
        count = 1;
        for (int j = i + 1; j < n; ++j) {
            if (arr[j] == arr[i])
                count++;
        }
        if (count > max_count) {
            max_count = count;
            mode_value = arr[i];
        }
    }

    printf("Mode: %d (occurs %d times)\n", mode_value, max_count);
}

int main() {
    int arr[] = {1, 2, 2, 3, 4};
    int n = sizeof(arr)/sizeof(arr[0]);

    printf("Mean: %.2f\n", calculate_mean(arr, n));
    printf("Median: %.2f\n", calculate_median(arr, n));
    calculate_mode(arr, n);

    return 0;
}
