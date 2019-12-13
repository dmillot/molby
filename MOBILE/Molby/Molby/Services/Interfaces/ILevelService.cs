using Molby.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services
{
    public interface ILevelService
    {
        Task<List<Level>> GetLevelsList();
        Task<Level> GetLevelById(int id);
    }
}
