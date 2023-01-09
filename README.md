Program do kompresji wykorzystujący kodowanie Huffmana.
(Jest to raczej prosty dokument przypominający readme, będzie on po polsku i mam nadzieje, że tym samym usprawni sprawdzenie mojej pracy.)

Metody użyte (podane w kolejności ich wywołania do osiągnięcia kompresji lub dekompresji):

Huffman.java :
----------KOMPRESJA----------

```public int huffman(String pathToRootDir, boolean compress);```
    Stwierdza który plik zamierzamy skompresować/zdekompresować.

```private int compress(String pathToRootDir);```
    Kompresuje wybrany plik.

```private int decompress(String pathToRootDir);```
    Dekompresuje wskazany plik.

```private HuffmanNode makeHuffmanTree(String filename);```
    Na początku do tablicy intów wpisujemy liczbę wystąpień znaków w pliku przy pomocy metody ```HuffmanIO.countCharFreqsFromFile(filename);``` po czym tworzymy dla każdej literki HuffmanNode z polami ```char sign``` oraz ```int frequency``` i wpisujemy je do obiektu klasy ```MinHeap``` (działa podobnie do priority queue). Parujemy HuffmanNode i tworzymy z nich drzewo, zwracamy roota.

```private int createDictionaryFromTree(HuffmanNode root);```
    Kodujemy słownik z ```HuffmanNode root``` do ArrayListy przy pomocy metody ```encodeChars(HuffmanNode root, String s)``` (korzystam z klasy NodeSignCode do przechowania tylko znaku i jego zerojedynkowej reprezentacji).

```private void encodeChars(HuffmanNode root, String s);```
    Rekurencyjnie koduje stringa złożonego z zer i jedynek dla danego znaku.

```private String makeBinaryString(String filename, HuffmanNode root);```
    Przechodzimy po pliku litera po literze i kodujemy ją przy pomocy wcześniej przygotowanego słownika.

----------KOMPRESJA-KONIEC----------

----------DEKOMPRESJA----------

```private int createDictionaryFromTree(HuffmanNode root)```
    Tworzy słownik przy pomocy wcześniej wczytanego drzewa zapisanego do pliku.

```private String convertBinToText(String binaryString, int unusedBits);```
    Konwersja wczytanego pliku tekstowego skompresowanego na taki do odczytu przez człowieka przy pomocy wygenerowanego słownika z poprzedniej metody.

----------DEKOMPRESJA-KONIEC---------

HuffmanIO.java :

```public static int[] countCharFreqsFromFile(String filename);```
    Zliczam wszystkie wystąpienia liter w pliku i zapisuję je w formie tablicy intów.

```private static int[] doubleResize(int[] signFrequencies);```
    Jeśli poprzednia metoda wymaga zwiększenia tablicy to zwiększam ją dwukrotnie.

```public static void writeByteToFile(String binaryString, HuffmanNode root, String filename);```
    Binarny string tworzony przez ```makeBinaryString(...)``` jest teraz modulowany przez 8 w celu sprawdzenia ile bitów trzeba dopisać aby cały string dzielił się na 8. Będzie to potrzebne do stworzenia bajtów z 8 bitów. Wyciągam z całego stringa wszystkie zera i jedynki do ostatniego bloku 8-bitowego, w którym to na początku dopisuję zera i doklejam z powrotem do poprzedniego Stringa. Przykład: String 001001001 reszta z dzielenia przez 8 to 7 zatem po podzieleniu na bloki będzie 00100100 , 1 po dopisaniu zer będzie 00100100 , 00000001 a po konkatenacji będzie 0010010000000001. Jestem świadom, że w przypadku %8 == 0 ze stringa 11111111 będzie 1111111100000000. Tak zapisanego stringa zamieniam na integera, którego castuję na byte i zapisuję tak do pliku.

```public static void saveTreeToFile(HuffmanNode root, String filepath);```
    Zapis drzewa do pliku korzystający z ```helpRecursiveMethod(...)``` Pierwszy znak to liczba nieużywanych bitów (tych co dopisywaliśmy wyżej) kolejne to drzewo.

```private static void helpRecursiveMethod(BufferedWriter bWriter, HuffmanNode node);```
    Zapis odbywa się następująco: jeśli na drzewie Huffmana jest liściem to zapisuję liczbę 1 i zaraz po niej znak do pliku .txt, natomiast jeśli nie jest liściem to zapisuję do pliku liczbę 0 i idę dalej najpierw po lewych dzieciach potem po prawym.

```public static HuffmanNode readTreeFromFile(String filename);```
    Odczyt HuffmanTree z pliku.

```private static HuffmanNode readTreeFromFileR(HuffmanNode root, BufferedReader br, boolean isFirst, int uB);```
    Analogiczny odczyt z pliku, HuffmanNode są jedynie pozbawione pola frequency, które nie jest potrzebne przy gotowym drzewie. Zwraca roota z drzewem pod nim.

```public static String readBinFiles(String filename, HuffmanNode root);```
    Odczyt pliku binarnego, ostatni bajt przy odkodowaniu sprawdzam w celu ustalenia, czy nie ma w nim niepotrzebnych bitów, jeśli są to usuwam je, jeśli nie mam to zostawiam i zwracam przeczytanego stringa zer i jedynek.

```public static int writeTextToFile(String filename, String text);```
    Po zamianie binaryStringa na znaki dzięki metodzie ```private String convertBinToText(String binaryString, int unusedBits);``` z Huffman.java zapisuję tekst do pliku .txt.

!!!!!!!!!!UWAGA!!!!!!!!!!
Nie zrobiono obsługi wyjątku w przypadku gdy używamy złego słownika (drzewa huffmana, inputDictionary.txt), to samo dla dobrego słownika, ale złego skompresowanego pliku.


