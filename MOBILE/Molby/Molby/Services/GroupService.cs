using Molby.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Net.Http;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services
{
    public class GroupService : IGroupService
    {
        public async Task<Group> GetGroupById(int id)
        {
            using (HttpClient client = new HttpClient())
            {
                try
                {
                    var response = await client.GetAsync("http://10.0.2.2/molby/public/api/groups/" + id);
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        var message = error != null ? error.Message : "";
                        throw new ApiException(message, response.StatusCode);

                    }

                    return await response.Content.ReadAsAsync<Group>();

                }
                catch (HttpRequestException ex)
                {
                    throw new ApiException("", false, ex);
                }
                catch (UnsupportedMediaTypeException ex)
                {
                    throw new ApiException("", false, ex);
                }

            }
        }

        public async Task<List<Group>> GetGroupsList()
        {
            using (HttpClient client = new HttpClient())
            {
                try
                {
                    var response = await client.GetAsync("http://10.0.2.2/molby/public/api/groups");
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        var message = error != null ? error.Message : "";
                        throw new ApiException(message, response.StatusCode);

                    }

                    return await response.Content.ReadAsAsync<List<Group>>();

                }
                catch (HttpRequestException ex)
                {
                    throw new ApiException("", false, ex);
                }
                catch (UnsupportedMediaTypeException ex)
                {
                    throw new ApiException("", false, ex);
                }

            }
        }
    }
}
