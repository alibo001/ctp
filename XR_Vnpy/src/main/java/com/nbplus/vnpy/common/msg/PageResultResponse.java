/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.nbplus.vnpy.common.msg;

import com.github.pagehelper.PageInfo;


/**
* Date: 2019/11/14
* Time: 11：00
* Version 1.0.0
* @Author gongtan
*/
public class PageResultResponse<T> extends BaseResponse {

   PageInfo<T> data;

   public PageResultResponse(PageInfo<T> data) {
       this.data = data;
   }

   public PageResultResponse() {
       this.data = null;
   }

   public PageInfo<T> getData() {
       return data;
   }

   public void setData(PageInfo<T> data) {
       this.data = data;
   }

}
