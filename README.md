# FileIO

Write a program that takes as input:
- a CSV (comma separated values) file-path
- base output directory where the output files would be written

Example: <csv-file-path> <base-out-directory>

Following is the structure of the information in the file:
operator, vertical, smsType, sendStatus, smsId, smsText

where 
- all the values are non-empty Strings.
- sendStatus : can be one of “SUCCESS” or “FAILURE”

The program should read the records and keep on writing them to separate files in the following hierarchy (directory structure):
<base-output-directory>/operator/vertical/smsType/sendStatus

The full record should be written to the output file. Keep the name of the output file the same as that of the original file.

Extra Points: The output files should be generated in batches of 100.
