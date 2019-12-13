using Molby.Models;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services
{
    public class BaseProvider
    {
        protected string _baseUrl;

        protected HttpClient GetClient()
        {
            return GetClient(_baseUrl);
        }

        protected virtual HttpClient GetClient(string baseUrl)
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri(baseUrl);

            return client;
        }

        protected async Task Get(string url)
        {
            using (HttpClient client = GetClient())
            {
                try
                {
                    var response = await client.GetAsync(url);
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        throw new ApiException(error.Message, response.StatusCode);
                    }
                }
                catch (HttpRequestException ex)
                {
                    throw new ApiException("", false, ex);
                }
            }
        }

        protected async Task<T> Get<T>(string url)
        {
            using (HttpClient client = GetClient())
            {
                try
                {
                    var response = await client.GetAsync(url);
                    if (!response.IsSuccessStatusCode)
                    {
                        var error = await response.Content.ReadAsAsync<ApiException>();
                        var message = error != null ? error.Message : "";
                        throw new ApiException(message, response.StatusCode);
                    }
                    return await response.Content.ReadAsAsync<T>();
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
