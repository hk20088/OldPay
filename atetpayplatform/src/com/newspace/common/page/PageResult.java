package com.newspace.common.page;

import java.io.Writer;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.struts2.components.Component;

import com.newspace.common.utils.StringUtils;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 用于封装分页参数和实现分页标签的类
 * @author huqili
 * @since jdk1.6 struts2.3.4
 * @version 1.0
 */
public class PageResult extends Component
{
	/**
	 * 总页码
	 */
	private int pageCount;
	/**
	 * 当前页码
	 */
	private int currentPage = 1;
	/**
	 * 总记录数
	 */
	private int recordCount;
	/**
	 * 上一页页码
	 */
	private int prePage;
	/**
	 * 下一页页码
	 */
	private int nextPage;
	/**
	 * 分页中显示的记录对应的Vo类对象集合
	 */
	private Collection<?> vos;
	/**
	 * 每一分页中显示的记录数量
	 */
	private int showCount;
	/**
	 * 翻页时访问的url
	 */
	private String url;
	/**
	 * 分页标签的样式的class属性
	 */
	private String styleClass;
	/**
	 * ValueStack对象
	 */
	private ValueStack stack;
	/**
	 * 查询的form
	 */
	private String form;

	/**
	 * 构造方法
	 * @param stack ValueStack对象
	 */
	public PageResult(ValueStack stack)
	{
		super(stack);
		this.stack = stack;
	}

	/**
	 * 构造方法
	 * @param stack ValueStack对象
	 * @param showCount 每一分页显示记录的数量
	 */
	public PageResult(ValueStack stack, int showCount)
	{
		this(stack);
		this.showCount = showCount;
	}

	/**
	 * 构造方法
	 * @param stack ValueStack对象
	 * @param showCount 每一分页显示记录的数量
	 * @param vos 需要分页显示的所有记录对应的vo类对象
	 */
	public PageResult(ValueStack stack, int showCount, Collection<?> vos)
	{
		this(stack);
		this.showCount = showCount;
		this.vos = vos;
	}

	@Override
	public boolean end(Writer writer, String body)
	{
		boolean result = super.start(writer);
		try
		{
			PageResult page = (PageResult) stack.findValue("page");
			StringBuffer str = new StringBuffer();
			String onClass = "on";
			String offClass = "off";
			String linkUrl = page.url + "&currentPage={0}";
			String[] strs = linkUrl.split("currentPage");
			if (strs.length > 2)
			{
				linkUrl = strs[0] + "currentPage" + strs[strs.length - 1];
			}
			if (linkUrl.indexOf("?") < 0)
			{
				linkUrl = linkUrl.replaceFirst("&", "?");
			}
			if (!StringUtils.isNullOrEmpty(form))
			{
				linkUrl = "javascript:" + form + ".action=''" + linkUrl + "'';";
				linkUrl += form + ".submit()";
			}
			MessageFormat format = new MessageFormat("<a href=\"{0}\" class=\"{1}\">{2}</a>");
			MessageFormat urlFormat = new MessageFormat(linkUrl);
			str.append("<div class=\"" + styleClass + "\">");
			if (page.getCurrentPage() == 1)
			{
				str.append("<span>&lt;上一页</span>");
			}
			else
			{
				str.append(format.format(new String[] {
						urlFormat.format(new Object[] { String.valueOf(page.getCurrentPage() - 1) }), offClass,
						"&lt;上一页" }));
			}
			// str.append(format.format(new String[]{urlFormat.format(new Object[]{"1"}),offClass,"首页"}));
			Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(2);
			set.add(page.pageCount);
			set.add(page.pageCount - 1);
			set.add(page.currentPage);
			for (int i = 1; i <= 2; i++)
			{
				set.add(page.currentPage - i);
				set.add(page.currentPage + i);
			}
			Iterator<Integer> iterator = set.iterator();
			while (iterator.hasNext())
			{
				int i = iterator.next();
				if (i <= 0 || i > page.pageCount)
				{
					iterator.remove();
				}
			}
			Integer[] arr = new Integer[set.size()];
			set.toArray(arr);
			Arrays.sort(arr);
			for (int i = 0; i < arr.length; i++)
			{
				if (i > 0 && (arr[i] - arr[i - 1]) > 1)
				{
					str.append("<span>···</span>");
				}
				if (arr[i] == page.getCurrentPage())
				{
					str.append("<span>" + page.getCurrentPage() + "</span>");
				}
				else
				{
					str.append(format.format(new String[] { urlFormat.format(new Object[] { String.valueOf(arr[i]) }),
							arr[i] == page.getCurrentPage() ? onClass : offClass, arr[i] + "" }));
				}
			}
			// str.append(format.format(new String[]{urlFormat.format(new
			// Object[]{page.getPageCount()}),offClass,"尾页"}));
			if (page.getCurrentPage() == page.getPageCount())
			{
				str.append("<span>下一页&gt;</span>");
			}
			else
			{
				str.append(format.format(new String[] {
						urlFormat.format(new Object[] { String.valueOf(page.getCurrentPage() + 1) }), offClass,
						"下一页&gt;" }));
			}
			str.append("</div>");
			writer.write(str.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public int getShowCount()
	{

		return showCount;
	}

	public void setShowCount(int showCount)
	{

		this.showCount = showCount;
	}

	public int getPageCount()
	{

		return pageCount;
	}

	public void setPageCount(int pageCount)
	{

		this.pageCount = pageCount;
	}

	public int getCurrentPage()
	{

		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{

		this.currentPage = currentPage;
	}

	public int getRecordCount()
	{

		return recordCount;
	}

	public void setRecordCount(int recordCount)
	{

		this.recordCount = recordCount;
	}

	public int getPrePage()
	{

		return prePage;
	}

	public void setPrePage(int prePage)
	{

		this.prePage = prePage;
	}

	public int getNextPage()
	{

		return nextPage;
	}

	public void setNextPage(int nextPage)
	{

		this.nextPage = nextPage;
	}

	public Collection<?> getVos()
	{

		return vos;
	}

	public void setVos(Collection<?> vos)
	{

		this.vos = vos;
	}

	public String getUrl()
	{

		return url;
	}

	public void setUrl(String url)
	{

		this.url = url;
	}

	public String getStyleClass()
	{

		return styleClass;
	}

	public void setStyleClass(String styleClass)
	{

		this.styleClass = styleClass;
	}

	public String getForm()
	{

		return form;
	}

	public void setForm(String form)
	{

		this.form = form;
	}

	public static void main(String[] args)
	{
		String str = "javascript:form.action=''linkUrl{0}'';";
		MessageFormat format = new MessageFormat(str);
		System.out.println(format.format(new String[] { "aaaaaaaa" }));

	}
}
