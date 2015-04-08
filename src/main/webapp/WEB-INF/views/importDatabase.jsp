Import your database to index and search against a local copy of it

<form id="sql-file-upload-form" method="POST"
	action="<script type='text/javascript'>uploadSQLFileToServer();</script>"
	enctype="multipart/form-data">
	Database SQL Source File to upload: <input type="file"
		name="sqlSourceFile"><br /><input type="text"
		name="dbName" placeholder="Database Name: "><br /> <input type="submit" value="Upload">
</form>
<div id="sql-upload-feedback"></div>
