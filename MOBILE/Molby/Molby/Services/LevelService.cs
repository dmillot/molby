using Molby.Models;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services
{
    public class LevelService : ILevelService
    {
        public async Task<Level> GetLevelById(int id)
        {
            using (HttpClient client = new HttpClient())
            {
                try
                {
                    var response = await client.GetAsync("http://10.0.2.2/molby/public/api/levels/" + id);
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

        public async Task<List<Level>> GetLevelsList()
        {
            using (HttpClient client = new HttpClient())
            {
                try
                {
                    var response = await client.GetAsync("http://10.0.2.2/molby/public/api/levels");
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        var message = error != null ? error.Message : "";
                        throw new ApiException(message, response.StatusCode);

                    }

                    return await response.Content.ReadAsAsync<List<Level>>();

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
