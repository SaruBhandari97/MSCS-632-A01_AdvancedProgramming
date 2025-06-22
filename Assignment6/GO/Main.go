package main

import (
	"fmt"
	"sync"
	"time"
)

type Task struct {
	ID int
}

func (t Task) Process() {
	time.Sleep(500 * time.Millisecond)
}

func worker(id int, tasks <-chan Task, wg *sync.WaitGroup) {
	defer wg.Done()
	for task := range tasks {
		fmt.Printf("Worker-%d processing task %d\n", id, task.ID)
		task.Process()
	}
}

func main() {
	taskChan := make(chan Task, 10)
	var wg sync.WaitGroup

	for i := 0; i < 3; i++ {
		wg.Add(1)
		go worker(i, taskChan, &wg)
	}

	for i := 1; i <= 10; i++ {
		taskChan <- Task{ID: i}
	}

	close(taskChan)
	wg.Wait()
	fmt.Println("All tasks processed.")
}