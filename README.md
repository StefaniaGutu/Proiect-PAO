# Aplicatie cabinet medical

### Tipuri de obiecte:
- Persoana 
  - Client
  - Doctor
- Programare
  - Programare cu Plata
  - Programare cu Asigurare
- Adresa
- Cabinet medical
---
- Main
- Service

### Lista de actiuni:
1. Afisare detalii cabinet
2. Adaugare client
3. Adaugare doctor
4. Adaugare programare
5. Stergere client
6. Stergere doctor
7. Stergere programare
8. Modificare data si ora programare
9. Adaugare afectiune pentru un pacient
10. Afisare istoric pacient
11. Afisare toti pacientii cu o afectiune data
12. Setare adresa cabinet

## Etapa 2
- servicii singleton generice pentru citirea si scrierea din fisiere de tip CSV
- incarcarea datelor din fisiere la pornirea programului
- serviciu de audit care scrie într-un fișier de tip CSV de fiecare data când este executată una dintre acțiunile descrise mai sus. Structura fișierului: nume_actiune, timestamp
