# Nexign Bootcamp 2024 testovoe

������ 1:
�������� ������, ����������� ������ �����������, �.�. ������������ CDR �����.
�������:
1.       1 CDR = 1 �����. �������������� ������ � ������ ������� - 1 ���;
2.       ������ � CDR ���� �� �� �������, �.�. ������ �� ������ �������� ����� ���� � ������ ������ �����;
3.       ���������� � ������������ ������� ������������ ��������� �������;
4.       ������������� ������ ��������� (�� ����� 10) �������� � ��������� �� (h2);
5.       ����� ��������� CDR, ������ � ����������� ������������ ���������� � �������� ������� ���� ��.
������ 2:
������ ���������� �� CDR ������� �������� � ������ ��������� UDR. ������������ ������ �� ������� �������� � �����.
�������:
1.       ������ ����� ����� ������ �� CDR �����. �� � ��������� ���������� � ��������, � ������� � ���, � ������ ������� ���.
2.       ��������������� ������� ������ ���������� � /reports.
������ �����: �����_�����.json (79876543221_1.json);
3.       ����� ���������� ������ ��������� ������:

-       generateReport() � ��������� ��� ������ � ������� � ������� ������� �� ����� ���������� � �������� �������� ������� �� ����� ��������������� ������� ������� ��������;
-       generateReport(msisdn) � ��������� ��� ������ � ������� � ������� ������� �� ������ �������� � ��� ��������� ������� ������� � ������ ������;
-       generateReport(msisdn, month) � ��������� ����� � ������� � ������� ������� �� ������ �������� � ��� ��������� ������� ������� � ��������� ������.

�������:

� ������ �������������� ���������� ���� �����������, ��� ��������� ���������� ��� CDR � UDR -�����������

� ���������� CDR:

- �������������� ������� ������
- ������ �� �������� ���� �� �� �������
- ������ ������������ ��������� �������
- ������ ��������� �������� � �� (�������� ������ abonents � transactions - SqlAdapterH2)

� ���������� UDR: 

- ������ ������� ������������� �� CDR-������
- ��������������� ������� ������ ����������� � ����� /reports
- ��������� UDR �������� 3 ���������� ������ generateReport()

