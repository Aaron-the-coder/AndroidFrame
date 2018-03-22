package com.goldencarp.lingqianbao.view.util;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.goldencarp.lingqianbao.model.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 文件下载（用于软件更新）
 * 
 * @author spoon
 * 
 */
public class FileUploader extends AsyncTask<Void, Integer, String>
{
	HttpURLConnection httpURLConnection;
	String urlString;
	FileUploadCallBack fileDownCallBack;
	HashMap<String, String> httpParams;
	String filePath;
	String result;

	public FileUploader(String urlString, String filePath, HashMap<String, String> httpParams, FileUploadCallBack fileDownCallBack)
	{
		this.urlString = urlString;
		this.filePath = filePath;
		this.fileDownCallBack = fileDownCallBack;
		this.httpParams = httpParams;
	}

	@Override
	protected void onPreExecute()
	{
		if (fileDownCallBack != null)
		{
			fileDownCallBack.onStart();
		}
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values)
	{
		if (fileDownCallBack != null)
		{
			fileDownCallBack.onProgress(values[0]);
		}
		super.onProgressUpdate(values);
	}

	/**
	 * 上传文件
	 */
	public class FormFile
	{
		/* 上传文件的数据 */
		private byte[] data;
		private InputStream inStream;
		private File file;
		/* 文件名称 */
		private String filname;
		/* 请求参数名称 */
		private String parameterName;
		/* 内容类型 */
		private String contentType = "application/octet-stream";

		public FormFile(String filname, byte[] data, String parameterName, String contentType)
		{
			this.data = data;
			this.filname = filname;
			this.parameterName = parameterName;
			if (contentType != null)
				this.contentType = contentType;
		}

		public FormFile(String filname, File file, String parameterName, String contentType)
		{
			this.filname = filname;
			this.parameterName = parameterName;
			this.file = file;
			try
			{
				this.inStream = new FileInputStream(file);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			if (contentType != null)
				this.contentType = contentType;
		}

		public File getFile()
		{
			return file;
		}

		public InputStream getInStream()
		{
			return inStream;
		}

		public byte[] getData()
		{
			return data;
		}

		public String getFilname()
		{
			return filname;
		}

		public void setFilname(String filname)
		{
			this.filname = filname;
		}

		public String getParameterName()
		{
			return parameterName;
		}

		public void setParameterName(String parameterName)
		{
			this.parameterName = parameterName;
		}

		public String getContentType()
		{
			return contentType;
		}

		public void setContentType(String contentType)
		{
			this.contentType = contentType;
		}

	}

	public static boolean post1(String path, Map<String, String> params, FormFile[] files) throws Exception
	{
		final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

		int fileDataLength = 0;
		for (FormFile uploadFile : files)
		{// 得到文件类型数据的总长度
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";filename=\"" + uploadFile.getFilname() + "\"\r\n");
			fileExplain.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.getInStream() != null)
			{
				fileDataLength += uploadFile.getFile().length();
			} else
			{
				fileDataLength += uploadFile.getData().length;
			}
		}
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet())
		{// 构造文本类型参数的实体数据
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// 计算传输给服务器的实体数据总长度
		int dataLength = textEntity.toString().getBytes().length + fileDataLength + endline.getBytes().length;

		URL url = new URL(path);
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		// 下面完成HTTP请求头的发送
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary=" + BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes());
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes());
		// 把所有文件类型的实体数据发送出来
		for (FormFile uploadFile : files)
		{
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";filename=\"" + uploadFile.getFilname() + "\"\r\n");
			fileEntity.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes());
			if (uploadFile.getInStream() != null)
			{
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1)
				{
					outStream.write(buffer, 0, len);
				}
				uploadFile.getInStream().close();
			} else
			{
				outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
			}
			outStream.write("\r\n".getBytes());
		}
		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		if (reader.readLine().indexOf("200") == -1)
		{// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
			return false;
		}
		outStream.flush();
		outStream.close();
		reader.close();
		socket.close();
		return true;
	}

	@Override
	protected String doInBackground(Void... params)
	{
		result = null;

		// Thread thread = new Thread(new Runnable()
		// {
		// @Override
		// public void run()
		// {
		try
		{
			StringBuffer stringBuffer = new StringBuffer();
			if (httpParams != null)
			{
				stringBuffer.append("?");
				Iterator<String> iterator = httpParams.keySet().iterator();
				while (iterator.hasNext())
				{
					String key = iterator.next();
					String value = httpParams.get(key);
					stringBuffer.append(key);
					stringBuffer.append("=");
					stringBuffer.append(URLEncoder.encode(value, "GB2312"));
					if (iterator.hasNext())
					{
						stringBuffer.append("&");
					}
				}
			}
			// String urlString = urlString + stringBuffer.toString();

			String end = "\r\n";
			String Hyphens = "--";
			String boundary = "*****";

			URL url = new URL(urlString + stringBuffer.toString());
			//Log.e("upload", url.toString());
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(Constants.TIME_OUT);
			httpURLConnection.setReadTimeout(Constants.TIME_OUT);

			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			httpURLConnection.connect();

			OutputStream outputStream = httpURLConnection.getOutputStream();
			stringBuffer = new StringBuffer();
			stringBuffer.append(Hyphens + boundary + end);
			stringBuffer.append("Content-Disposition: form-data; " + "name=\"file1\";filename=\"error\"" + end);
			stringBuffer.append(end);
			outputStream.write(stringBuffer.toString().getBytes());

			FileInputStream inputStream = new FileInputStream(filePath);
			int length = inputStream.available();
			byte[] data = new byte[4 * 1024];
			int n = -1;
			int count = 0;
			while ((n = inputStream.read(data)) != -1)
			{
				outputStream.write(data, 0, n);
				count += n;
				publishProgress((int) ((count * 1.0f / length) * 100));
			}
			inputStream.close();

			outputStream.write(end.getBytes());
			outputStream.write((Hyphens + boundary + Hyphens + end).getBytes());

			stringBuffer = new StringBuffer();
			if (httpParams != null)
			{
				Iterator<String> iterator = httpParams.keySet().iterator();
				while (iterator.hasNext())
				{
					String key = iterator.next();
					String value = httpParams.get(key);

					stringBuffer.append(Hyphens + boundary + end);
					stringBuffer.append("Content-Disposition: form-data;name=\"" + key + "\"" + end + end + value);
					stringBuffer.append(end);
					stringBuffer.append(Hyphens + boundary + Hyphens + end);
					outputStream.write(stringBuffer.toString().getBytes());
				}
			}

			outputStream.flush();

			InputStream in = httpURLConnection.getInputStream();
			String encoding = httpURLConnection.getContentEncoding();
			if (TextUtils.isEmpty(encoding))
			{
				encoding = "GB2312";
			}
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, encoding));
			StringBuffer resultBuffer = new StringBuffer();
			String ret = null;
			while ((ret = bufferedReader.readLine()) != null)
			{
				resultBuffer.append(ret);
			}
			result = resultBuffer.toString();

		} catch (Exception e)
		{
//			Logger.e("upload", e);
			e.printStackTrace();
		} finally
		{
			if (httpURLConnection != null)
			{
				httpURLConnection.disconnect();
			}
		}
		// }
		// });
		// thread.start();

		// int i = 0;
		// while (thread.isAlive())
		// {
		// try
		// {
		// Thread.sleep(300);
		// } catch (Exception e)
		// {
		// }
		// if (++i > 100)
		// break;
		// }

		return result;
	}

	@Override
	protected void onPostExecute(String result)
	{
//		Logger.e("upload-onPostExecute", result);
		if (result != null)
		{
			if (fileDownCallBack != null)
			{
				fileDownCallBack.onDownSuccess(result);
			}
		} else
		{
			if (fileDownCallBack != null)
			{
				fileDownCallBack.onDownError();
			}
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onCancelled()
	{
		if (httpURLConnection != null)
		{
			httpURLConnection.disconnect();
		}
		super.onCancelled();
	}

	/**
	 * 文件下载回调接口
	 * 
	 * @author spoon
	 * 
	 */
	public interface FileUploadCallBack
	{
		void onStart();

		void onProgress(int proess);

		void onDownSuccess(String result);

		void onDownError();
	}

}
