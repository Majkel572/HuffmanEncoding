package pl.edu.pw.ee;

public class MinHeap<T extends Comparable<T>> {

	T[] nums;
	int size;
	int j;

	public MinHeap(int size) {
		this.size = size;
		j = 0;
		nums = (T[]) new Comparable[this.size];
	}

	private int getParentIndex(int index) {
		if (index == 0)
			return 0;
		return ((index - 1) / 2);
	}

	private int getLeftChildIndex(int index) {
		return (index * 2 + 1);
	}

	private int getRightChildIndex(int index) {
		return (index * 2 + 2);
	}

	private void swap(int a, int b) {
		T tmp;
		tmp = nums[a];
		nums[a] = nums[b];
		nums[b] = tmp;
	}

	private void heapUp(int largest_index) {
		T toMove = nums[largest_index];
		if (nums[getParentIndex(largest_index)].compareTo(toMove) > 0) {
			swap(getParentIndex(largest_index), largest_index);
			largest_index = getParentIndex(largest_index);
			heapUp(largest_index);
		}

	}

	private void heapDown(int index) {
		int largest = index;
		int leftChild = getLeftChildIndex(index);
		int rightChild = getRightChildIndex(index);

		if (leftChild < size() && (nums[leftChild].compareTo(nums[largest]) < 0))
			largest = leftChild;
		if (rightChild < size() && (nums[rightChild].compareTo(nums[largest]) < 0))
			largest = rightChild;
		if (largest != index) {
			swap(index, largest);
			heapDown(largest);
		}
	}

	public int size() {
		return j;
	}

	public void put(T item) {
		if (item == null) {
			throw new IllegalArgumentException("Don't put null on heap.");
		}
		nums[j] = item;
		heapUp(j);
		j++;
	}

	public T pop() {
		if (size() <= 0) {
			return null;
		}
		T tmp = nums[0];
		swap(size() - 1, 0);
		j--;
		heapDown(0);
		return tmp;
	}

	public void sort(double[] nums) {
		if (nums == null)
			throw new NullPointerException();
		int size = nums.length;
		MinHeap<Double> heap = new MinHeap<Double>(size);
		for (int i = 0; i < size; i++) {
			heap.put(nums[i]);
		}
		for (int i = 0; i < size; i++) {
			nums[size - 1 - i] = heap.pop();
		}
	}
}