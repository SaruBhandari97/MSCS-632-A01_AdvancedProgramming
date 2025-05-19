fn main() {
    let data = vec![1, 2, 3, 4, 5]; 
    print_vector(&data);
    
    println!("Original vector: {:?}", data);
}

fn print_vector(vec: &Vec<i32>) {
    println!("Borrowed vector: {:?}", vec);
}