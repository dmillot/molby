using Molby.Models;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services
{
    public class UserService : IUserService
    {
        public async Task<User> GetUserById(int id)
        {
            using (HttpClient client = new HttpClient())
            {
                try
                {
                    var response = await client.GetAsync("http://10.0.2.2/molby/public/api/developers/" + id);
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        var message = error != null ? error.Message : "";
                        throw new ApiException(message, response.StatusCode);
                    }

                    return await response.Content.ReadAsAsync<User>();

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

        public async Task<Level> GetUserCurrentLevel(int id)
        {
            using (HttpClient client = new HttpClient())
            {
                try
                {
                    var response = await client.GetAsync("http://10.0.2.2/molby/public/api/developers/" + id + "/level");
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        var message = error != null ? error.Message : "";
                        throw new ApiException(message, response.StatusCode);

                    }

                    return await response.Content.ReadAsAsync<Level>();

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
